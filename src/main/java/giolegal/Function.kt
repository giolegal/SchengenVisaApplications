package giolegal

import java.util.*
import com.microsoft.azure.functions.annotation.*
import com.microsoft.azure.functions.*

/**
 * Azure Functions with HTTP Trigger.
 */
class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke function deployed to Azure.
     * More details: https://aka.ms/functions_authorization_keys
     */
    @FunctionName("HttpTrigger")
    fun run(
            @HttpTrigger(name = "req", methods = [HttpMethod.GET, HttpMethod.POST], authLevel = AuthorizationLevel.ANONYMOUS) request: HttpRequestMessage<Optional<String>>,
            context: ExecutionContext): HttpResponseMessage {
        context.logger.info("Java HTTP trigger processed a request.")

        // Parse query parameter
        val query = request.queryParameters["name"]
        val name = request.body.orElse(query)

        return if (name == null) {
            request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build()
        } else {
            request.createResponseBuilder(HttpStatus.OK).body("Hello, $name").build()
        }
    }
}
