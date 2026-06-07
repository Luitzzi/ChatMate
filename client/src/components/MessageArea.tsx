import {Virtuoso} from 'react-virtuoso';
import {ChatBubble} from "./ChatBubble.tsx";
import type {IncomingMessage} from "../generated/proto-types.ts";


export type MessageAreaProps = {
    messages: IncomingMessage[];
}

export const MessageArea = ({messages}: MessageAreaProps) => {
    return (
        <div style={{
            flex: 1,
            minHeight: 0,
        }}>
            <Virtuoso
                data={messages}
                followOutput={"smooth"}
                itemContent={(_index, message) => {
                    let hasSameSender = false;
                    if (_index > 0) {
                        const prev = messages[_index - 1];
                        hasSameSender = prev.senderId === message.senderId;
                    }
                    return (<ChatBubble message={message} printSenderName={!hasSameSender}/>);
                }}
            />
        </div>
    );
}
