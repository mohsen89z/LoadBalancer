package xyz.homapay.hampay.balancer.service;

import xyz.homapay.hampay.common.common.request.RequestHeader;
import xyz.homapay.hampay.common.common.request.RequestMessage;
import xyz.homapay.hampay.common.common.request.RequestService;

/**
 * Created by mushtu on 12/28/15.
 */
public class RequestMessageBuilderService {


    public  RequestMessage buildMessage(RequestService service)
    {
        RequestMessage<RequestService> message = new RequestMessage<RequestService>();
        RequestHeader header = new RequestHeader();
        header.setAuthToken("valid-auth"); //todo set valid auth token
        header.setVersion("valid-version"); //todo set valid version
        message.setRequestHeader(header);
        message.setService(service);
        return message;
    }
}
