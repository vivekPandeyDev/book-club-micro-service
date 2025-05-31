package org.loop.troop.response;

import java.util.Map;

public record ApiResponse<T>(boolean success, Map<String, T> data, String message) {
}
