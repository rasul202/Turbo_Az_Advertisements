package org.example.web_scrapt.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusConstants {

    ACTIVE(1) , DE_ACTIVE(0);

    private final Integer status;

}
