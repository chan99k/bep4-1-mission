package com.back.boundedcontext.market.out;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.back.global.exception.DomainException;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class TossPaymentsService {
	private static final String TOSS_BASE_URL = "https://api.tosspayments.com";
	private static final String CONFIRM_PATH = "/v1/payments/confirm";

	private final RestClient tossRestClient;
	private final ObjectMapper objectMapper;

	@Value("${custom.market.toss.payments.secretKey:}")
	private String tossSecretKey;

	public TossPaymentsService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.tossRestClient = RestClient.builder()
			.baseUrl(TOSS_BASE_URL)
			.build();
	}

	/**
	 * 토스 페이먼츠 API를 호출하여 카드 결제 승인을 요청합니다.
	 *
	 * <p>클라이언트로부터 전달받은 결제 정보(paymentKey, orderId, amount)를 사용하여
	 * 토스 페이먼츠 서버에 최종 결제 승인을 요청하고, 결과를 반환합니다.</p>
	 *
	 * @param paymentKey 토스 페이먼츠에서 발급한 결제 건에 대한 고유 키
	 * @param orderId    상점에서 발급한 주문 ID (토스 페이먼츠 요청 시 사용된 ID와 일치해야 함)
	 * @param amount     결제할 금액
	 * @return 결제 승인 성공 시, 토스 페이먼츠로부터 반환된 결제 상세 정보를 담은 Map 객체
	 * @throws DomainException 다음의 경우에 발생:
	 *                         <ul>
	 *                           <li>HTTP 응답 코드가 200이 아닌 경우 (에러 코드로 매핑됨)</li>
	 *                           <li>응답 본문이 비어있는 경우 ("400-EMPTY_RESPONSE")</li>
	 *                           <li>API 호출 중 네트워크 오류 등의 예외가 발생한 경우 ("400-TOSS_CALL_EXCEPTION")</li>
	 *                         </ul>
	 */
	public Map<String, Object> confirmCardPayment(String paymentKey, String orderId, long amount) {
		TossPaymentsConfirmRequest requestBody = new TossPaymentsConfirmRequest(
			paymentKey,
			orderId,
			amount
		);

		try {
			ResponseEntity<Map> responseEntity = createConfirmRequest(requestBody)
				.retrieve()
				.toEntity(Map.class);

			int httpStatus = responseEntity.getStatusCode().value();
			Map<String, Object> responseBody = responseEntity.getBody();

			if (httpStatus != 200) {
				throw createDomainExceptionFromNon200(httpStatus, responseBody);
			}

			if (responseBody == null) {
				throw new DomainException("400-EMPTY_RESPONSE", "토스 결제 승인 응답 바디가 비었습니다.");
			}

			@SuppressWarnings("unchecked")
			Map<String, Object> casted = (Map<String, Object>)responseBody;
			return casted;

		} catch (RestClientResponseException e) {
			throw createDomainExceptionFromHttpError(e);
		} catch (DomainException e) {
			throw e;
		} catch (Exception e) {
			throw new DomainException("400-TOSS_CALL_EXCEPTION", "토스 결제 승인 호출 중 예외: " + e.getMessage());
		}
	}

	/**
	 * 토스 페이먼츠 결제 승인 API 호출을 위한 {@link RestClient} 요청 스펙을 생성합니다.
	 *
	 * <p>이 메서드는 HTTP POST 요청을 준비하며, API 호출에 필요한 기본 인증(Basic Auth),
	 * 컨텐츠 타입, 수용 가능한 응답 타입 및 요청 본문을 설정합니다.</p>
	 *
	 * @param requestBody 결제 승인 요청 정보를 담고 있는 DTO
	 * @return 설정이 완료된 {@link RestClient.RequestHeadersSpec} 객체
	 */
	private RestClient.RequestHeadersSpec<?> createConfirmRequest(TossPaymentsConfirmRequest requestBody) {
		return tossRestClient.post()
			.uri(CONFIRM_PATH)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.headers(headers -> headers.setBasicAuth(tossSecretKey, ""))
			.body(requestBody);
	}

	/**
	 * 성공(200 OK)이 아닌 HTTP 응답을 {@link DomainException}으로 변환합니다.
	 *
	 * <p>토스 페이먼츠 응답 본문에서 에러 코드('code')와 메시지('message')를 추출하여
	 * 시스템 내에서 사용할 수 있는 예외 객체로 가공합니다. 응답 본문이 없는 경우
	 * HTTP 상태 코드를 기반으로 기본 예외를 생성합니다.</p>
	 *
	 * @param httpStatus   HTTP 상태 코드
	 * @param responseBody 토스 페이먼츠로부터 받은 에러 응답 본문 (Map 형태)
	 * @return 가공된 정보를 담은 {@link DomainException} 객체
	 */
	private DomainException createDomainExceptionFromNon200(int httpStatus, Map responseBody) {
		if (responseBody == null) {
			return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(응답 바디 없음), HTTP " + httpStatus);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> body = (Map<String, Object>)responseBody;

		String tossCode = extractStringOrDefault(body, "code", "HTTP_" + httpStatus);
		String tossMessage = extractStringOrDefault(body, "message", "토스 결제 승인 실패, HTTP " + httpStatus);

		return new DomainException("400-" + tossCode, tossMessage);
	}

	/**
	 * HTTP 통신 예외({@link RestClientResponseException})를 {@link DomainException}으로 변환합니다.
	 *
	 * <p>REST 클라이언트 호출 중 에러 응답(4xx, 5xx)이 발생했을 때 호출됩니다.
	 * 응답 본문이 JSON 형태인 경우 이를 파싱하여 토스의 에러 코드와 메시지를 추출하며,
	 * 파싱에 실패하거나 본문이 비어있는 경우 HTTP 상태 코드를 포함한 기본 메시지를 사용합니다.</p>
	 *
	 * @param e 발생한 REST 클라이언트 응답 예외
	 * @return 토스의 에러 정보를 분석하여 생성된 {@link DomainException} 객체
	 */
	private DomainException createDomainExceptionFromHttpError(RestClientResponseException e) {
		int httpStatus = e.getStatusCode().value();
		String rawBody = e.getResponseBodyAsString(StandardCharsets.UTF_8);

		if (rawBody.isBlank()) {
			return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(빈 바디), HTTP " + httpStatus);
		}

		try {
			Map<String, Object> errorBody = objectMapper.readValue(rawBody, new TypeReference<>() {
			});
			String tossCode = extractStringOrDefault(errorBody, "code", "HTTP_" + httpStatus);
			String tossMessage = extractStringOrDefault(errorBody, "message", "토스 결제 승인 실패, HTTP " + httpStatus);
			return new DomainException("400-" + tossCode, tossMessage);
		} catch (Exception parseFail) {
			return new DomainException("400-HTTP_" + httpStatus,
				"토스 결제 승인 실패, HTTP " + httpStatus + " / body=" + rawBody);
		}
	}

	/**
	 * Map에서 특정 키에 해당하는 문자열 값을 추출하며, 유효하지 않을 경우 기본값을 반환합니다.
	 *
	 * <p>값이 존재하고, 해당 값이 {@link String} 타입이며, 비어있지 않은(non-blank) 경우에만
	 * 해당 값을 반환합니다. 그 외의 모든 경우에는 제공된 {@code defaultValue}를 반환합니다.</p>
	 *
	 * @param map          데이터를 추출할 원본 Map
	 * @param key          추출할 값의 키
	 * @param defaultValue 값이 없거나 유효하지 않을 경우 반환할 기본값
	 * @return 추출된 문자열 또는 기본값
	 */
	private String extractStringOrDefault(Map<String, Object> map, String key, String defaultValue) {
		Object value = map.get(key);
		if (value instanceof String s && !s.isBlank())
			return s;
		return defaultValue;
	}

	public record TossPaymentsConfirmRequest(String paymentKey, String orderId, long amount) {
	}
}
