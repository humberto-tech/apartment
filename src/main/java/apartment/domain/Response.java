package apartment.domain;

import java.util.ArrayList;
import java.util.List;

public class Response {

    protected List<String> messages = new ArrayList<>();

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }


}
