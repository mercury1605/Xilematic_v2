package demo;


import java.util.List;
import java.util.stream.Collectors;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;


public class LLM {


    public String generateResponse(List<Message> messages) {
// Initialize OpenAI client using environment variables
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey("") // API key của bạn
                .build();

        // Transform custom Message objects to OpenAI's ChatCompletionMessageParam objects
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1)
                .maxTokens(1024);

        // Add messages individually to the builder
        for (Message message : messages) {
            if (message.getRole().equals("system")) {
                ChatCompletionSystemMessageParam systemMsg = ChatCompletionSystemMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(systemMsg);
            } else if (message.getRole().equals("user")) {
                ChatCompletionUserMessageParam userMsg = ChatCompletionUserMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(userMsg);
            } else {
                // For assistant or other roles, use ChatCompletionAssistantMessageParam
                ChatCompletionAssistantMessageParam assistantMsg = ChatCompletionAssistantMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(assistantMsg);
            }
        }

        // Get completion response
        ChatCompletion completion = client.chat().completions().create(paramsBuilder.build());

        // Return content from first choice
        return completion.choices().get(0).message().content().get();
    }

}