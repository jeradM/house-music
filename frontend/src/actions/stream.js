export const setStream = (streamId) => (dispatch, getState) => {
    const { ws, modal } = getState();
    const { client } = modal;
    const data = {
        id: client,
        type: 'SET_STREAM',
        params: { streamId }
    }
    send(ws, data);
}

const send = (ws, data) => {
  ws.send('/app/cmd', {}, JSON.stringify(data));
};