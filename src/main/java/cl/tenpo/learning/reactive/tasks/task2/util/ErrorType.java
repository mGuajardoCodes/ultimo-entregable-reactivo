package cl.tenpo.learning.reactive.tasks.task2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum ErrorType {

    missing_value("Missing %s %s"),
    invalid_body("The provided request body is not valid"),
    invalid_method("The request method %s is not supported"),
    percentage_internal_error("There was an unexpected exception retrieving the percentage"),

    bad_authorized_user_request("Cannot update a user that does not exist in the database"),
    percentage_service_unavailable("No cached percentage available and external service failed");

    private final String message;
}
