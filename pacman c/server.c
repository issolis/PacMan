#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <WinSock2.h>

#pragma comment(lib, "ws2_32.lib")

SOCKET serverSocket;

int initServer(int serverPort) {
    WSADATA wsaData;

    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        fprintf(stderr, "Error al inicializar Winsock.\n");
        return 1;
    }

    serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocket == INVALID_SOCKET) {
        fprintf(stderr, "Error al crear el socket del servidor.\n");
        WSACleanup();
        return 1;
    }

    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(serverPort);
    serverAddr.sin_addr.s_addr = INADDR_ANY;

    if (bind(serverSocket, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        fprintf(stderr, "Error al enlazar el socket del servidor.\n");
        closesocket(serverSocket);
        WSACleanup();
        return 1;
    }

    if (listen(serverSocket, 1) == SOCKET_ERROR) {
        fprintf(stderr, "Error al poner el servidor en modo escucha.\n");
        closesocket(serverSocket);
        WSACleanup();
        return 1;
    }

    return 0;
}

void receiveMessage() {
    struct sockaddr_in clientAddr;
    int clientAddrSize = sizeof(clientAddr);
    SOCKET clientSocket = accept(serverSocket, (struct sockaddr*)&clientAddr, &clientAddrSize);

    if (clientSocket == INVALID_SOCKET) {
        fprintf(stderr, "Error al aceptar la conexión del cliente.\n");
        closesocket(serverSocket);
        WSACleanup();
        return;
    }

    char buffer[256];
    int bytesRead = recv(clientSocket, buffer, sizeof(buffer), 0);

    if (bytesRead > 0) {
        buffer[bytesRead] = '\0';
        printf("Mensaje del cliente: %s\n", buffer);

        const char* response = "RECIBIDO";
        send(clientSocket, response, strlen(response), 0);
    }

    closesocket(clientSocket);
}

int main() {
    if (initServer(12345) != 0) {
        return 1;
    }

    while (1) {
        receiveMessage();
    }

    closesocket(serverSocket);
    WSACleanup();

    return 0;
}
