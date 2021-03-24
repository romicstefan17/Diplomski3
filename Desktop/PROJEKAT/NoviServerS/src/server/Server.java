/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import controller.Controller;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ClientRequestHandler;

/**
 *
 * @author SystemX
 */
public class Server extends Thread {

    boolean kraj = false;
    ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);

            while (!kraj) {
                System.out.println("Cekanje konekcije...");
                Socket socket = serverSocket.accept();
                System.out.println("Konektovan...");
                ClientRequestHandler crh = new ClientRequestHandler(socket,Controller.getInstance().getLoggedInUser());
                Controller.getInstance().addChr(crh);

                handleClient(socket);
            }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleClient(Socket socket) throws Exception {
        ClientRequestHandler start = new ClientRequestHandler(socket,Controller.getInstance().getLoggedInUser());
        start.start();
    }

    public void stopServer() {
        kraj = true;
        try {
            serverSocket.close();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
