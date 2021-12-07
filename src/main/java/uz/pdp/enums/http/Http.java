package uz.pdp.enums.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Http {
    HTTP_102(102, "Processing"),
    HTTP_201(201, "Created"),
    HTTP_202(202, "Accepted"),
    HTTP_204(204, "No Content"),
    HTTP_208(208, "Already Reported"),
    HTTP_401(401, "UnAuthorized"),
    HTTP_403(403, "Forbidden"),
    HTTP_404(404, "Not Found"),
    HTTP_405(405, "Method not allowed"),
    HTTP_408(408, "Request Timout");
    private final int code;
    private final String description;
}
