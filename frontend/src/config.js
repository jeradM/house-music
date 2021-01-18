export const SERVER_URL = process.env.REACT_APP_SERVER_URL || window.location.origin;
export const WS_URL = process.env.REACT_APP_WS_URL || `${window.location.origin.replace('http', 'ws')}/ws`;
