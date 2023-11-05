#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <WinSock2.h>

#pragma comment(lib, "ws2_32.lib")

SOCKET clientSocket;

int initClient(const char* serverIP, int serverPort) {
    WSADATA wsaData;

    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        fprintf(stderr, "Error al inicializar Winsock.\n");
        return 1;
    }

    clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        fprintf(stderr, "Error al crear el socket del cliente.\n");
        WSACleanup();
        return 1;
    }

    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(serverPort);
    serverAddr.sin_addr.s_addr = inet_addr(serverIP);

    if (connect(clientSocket, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        fprintf(stderr, "Error al conectar con el servidor.\n");
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    return 0;
}

void sendMessage(const char* message) {
    send(clientSocket, message, strlen(message), 0);

    char response[256];
    int bytesRead = recv(clientSocket, response, sizeof(response), 0);

    if (bytesRead > 0) {
        response[bytesRead] = '\0';
        printf("Respuesta del servidor: %s\n", response);
    }
}

int main() {
    if (initClient("127.0.0.1", 12345) != 0) {
        return 1;
    }

    char message[256];

    while (1) {
        printf("Escribe un mensaje: ");
        if (fgets(message, sizeof(message), stdin) == NULL) {
            break;
        }
        sendMessage(message);
    }

    closesocket(clientSocket);
    WSACleanup();

    return 0;
}
