import React from 'react';
import { UserCircleIcon } from '@heroicons/react/24/solid'
import {cutDirection} from "~/utils/MessagesUtils";

type ChatProps = {
    data: Array<string>;
}

const Chat: React.FC<ChatProps> = ({ data }) => {
    return (
        <div className="flex-1">
            {data.map((message, key) => {
                if (message.trim().endsWith("in")) {
                    return (
                        <div className="chat chat-start" key={`message-${key}`}>
                            <div className="chat-image avatar">
                                <div className="w-12 rounded-full bg-base-100">
                                    <UserCircleIcon className="h-12 w-12" />
                                </div>
                            </div>
                            <div className="chat-header">
                                User
                            </div>
                            <div className="chat-bubble">
                                {cutDirection(message, "in")}
                            </div>
                        </div>
                    )
                }

                return (
                    <div className="chat chat-end" key={`message-${key}`}>
                        <div className="chat-image avatar">
                            <div className="w-10 rounded-full">
                                <img src="gazprombank-1.svg" />
                            </div>
                        </div>
                        <div className="chat-header">
                            Operator
                        </div>
                        <div className="chat-bubble chat-bubble-primary">
                            {cutDirection(message, "out")}
                        </div>
                    </div>
                )
            })}
        </div>

    );
};

export default Chat;
