export const cutDirection = (message: string, direction: string) => message.substring(0, message.lastIndexOf(direction));

export const cutUnknownDirection = (message: string) => {
    if (message.trim().endsWith("in")) {
        return message.substring(0, message.lastIndexOf("in"));
    } else if (message.trim().endsWith("out")) {
        return message.substring(0, message.lastIndexOf("out"));
    }

    return message;
};