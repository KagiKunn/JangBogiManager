package com.kkh.jangbogimanager.common;

import java.util.UUID;

public class CommonService {
	private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public String generateInviteCode() {
		UUID uuid = UUID.randomUUID();
		long mostSigBits = uuid.getMostSignificantBits();
		return encodeBase62(mostSigBits);
	}

	private String encodeBase62(long value) {
		value = Math.abs(value); // 음수를 양수로 변환

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) { // 8자리 생성
			sb.append(BASE62.charAt((int) (value % 62)));
			value /= 62;
		}
		return sb.toString();
	}

}
