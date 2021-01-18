import { SERVER_URL } from "./config";

const fetchOptions = (method = 'get', data = null) => {
  const opts = {
    method: method || 'post',
    headers: {
      'Content-Type': 'application/json'
    }
  };
  if (data)
    opts.data = JSON.stringify(data);
  return opts;
};

export function send(path, method = 'get', data, callback) {
  const fetchOpts = fetchOptions(method, data);
  const prom = fetch(`${SERVER_URL}/${path}`, fetchOpts);
  if (callback) {
    prom.then(res => res.json())
      .then(json => callback(json));
  } else {
    return prom;
  }
}

export async function retrieve(path) {
  const resp = await fetch(`${SERVER_URL}/${path}/`);
  return await resp.json();
}
