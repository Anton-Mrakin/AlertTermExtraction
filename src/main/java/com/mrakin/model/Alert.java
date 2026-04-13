package com.mrakin.model;

import java.util.List;

public record Alert(String id, List<Content> contents, String date, String inputType) {
}
