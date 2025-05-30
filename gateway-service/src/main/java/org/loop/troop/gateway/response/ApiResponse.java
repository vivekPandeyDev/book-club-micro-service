package org.loop.troop.gateway.response;


import java.util.Map;

public record ApiResponse<T>(boolean success, Map<String, T> data,String message) {
}
