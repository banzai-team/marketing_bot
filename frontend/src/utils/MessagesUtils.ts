export const cutDirection = (message, direction) => message.substring(0, message.lastIndexOf(direction));

export const cutUnknownDirection = (message) => {
    if (message.trim().endsWith("in")) {
        return message.substring(0, message.lastIndexOf("in"));
    }

    return message.substring(0, message.lastIndexOf("out"));
};