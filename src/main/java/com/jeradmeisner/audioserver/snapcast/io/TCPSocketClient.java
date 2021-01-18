package com.jeradmeisner.audioserver.snapcast.io;

import com.jeradmeisner.audioserver.snapcast.interfaces.TCPSocketListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPSocketClient {

    private AtomicBoolean loop;
    private Socket socket;
    private PrintWriter socketWriter;
    private BufferedReader socketReader;
    private BlockingQueue<String> messageQueue;
    private List<TCPSocketListener> listeners;

    private String host;
    private int port;

    public TCPSocketClient(String host, int port, TCPSocketListener listener) {
        this.loop = new AtomicBoolean(false);
        this.messageQueue = new LinkedBlockingQueue<>();
        this.listeners = new CopyOnWriteArrayList<>();
        this.listeners.add(listener);
        this.host = host;
        this.port = port;
    }

    public void connect() {
        Thread readThread = new Thread(new SocketReader());
        readThread.start();
    }

    public void stop() {
        loop.set(false);

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            if (socketWriter != null) {
                socketWriter.flush();
                socketWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        socketWriter = null;
        socketReader = null;
        socket = null;
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void addToListeners(TCPSocketListener listener) {
        listeners.add(listener);
    }

    public void send(String message) {
        messageQueue.offer(message);
    }

    public class SocketWriter implements Runnable {
        @Override
        public void run() {
            while (loop.get()) {
                try {
                    String message = messageQueue.poll(50, TimeUnit.MILLISECONDS);
                    if (message != null && socketWriter != null) {
                        socketWriter.println(message + "\r\n");
                        socketWriter.flush();
                    }
                } catch (InterruptedException ignore) {

                }
            }
        }
    }

    public class SocketReader implements Runnable {

        @Override
        public void run() {
            try {
                socket = new Socket(host, port);
                socketWriter = new PrintWriter(socket.getOutputStream());
                socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                loop.set(true);

                Thread listenThread = new Thread(new SocketWriter());
                listenThread.start();

                onConnected();

                while (loop.get()) {
                    String msg = socketReader.readLine();
                    if (msg == null) continue;
                    onMessage(msg);
                }

            } catch (UnknownHostException e) {
                System.out.println("Unknown host: " + e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                stop();
                onDisconnected();
            }
        }

        private void onConnected() {
            listeners.forEach(TCPSocketListener::onConnected);
        }

        private void onMessage(final String message) {
            listeners.forEach(listener -> listener.onMessage(message));
        }

        private void onDisconnected() {
            listeners.forEach(TCPSocketListener::onDisconnected);
        }
    }
}
