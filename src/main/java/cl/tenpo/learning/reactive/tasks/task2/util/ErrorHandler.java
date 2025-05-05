package cl.tenpo.learning.reactive.tasks.task2.util;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BadRequestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.ServiceUnavailableException;

import static cl.tenpo.learning.reactive.tasks.task2.util.ErrorType.missing_value;
import static cl.tenpo.learning.reactive.tasks.task2.util.ErrorType.bad_authorized_user_request;
import static cl.tenpo.learning.reactive.tasks.task2.util.ErrorType.percentage_service_unavailable;

public final class ErrorHandler {

    private ErrorHandler() { }

    public static BaseException restException(
            final ErrorMapper mapper, final RestException exception, final Object... values) {
        return ErrorMapper.mapRestException(mapper, exception, values);
    }

    public static BadRequestException missingRequestValue(final String label, final String header) {
        return new BadRequestException(
            missing_value.name(), String.format(missing_value.message(), label, header));
    }

    public static BadRequestException invalidRequest(final String label) {
        return new BadRequestException(
                missing_value.name(), String.format(bad_authorized_user_request.message(), label));
    }

    public static ServiceUnavailableException serviceUnavailableException(final String label) {
        return new ServiceUnavailableException(String.format(percentage_service_unavailable.message(), label));
    }

}
