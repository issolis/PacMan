#include <stdio.h>
#include <winsock2.h>

int main() {
    // Inicializar Winsock
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        fprintf(stderr, "Error al inicializar Winsock\n");
        return 1;
    }

    // Crear socket del cliente
    SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        fprintf(stderr, "Error al crear el socket del cliente\n");
        WSACleanup();
        return 1;
    }

    // Configurar dirección del servidor
    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");  // Cambia la dirección IP según la del servidor
    serverAddr.sin_port = htons(12345);

    // Conectar al servidor
    if (connect(clientSocket, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        fprintf(stderr, "Error al conectar al servidor\n");
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    char message[512];
    const char* response = "RECIBIDO";

    while (1) {
        printf("Escribe un mensaje (o 'exit' para salir): ");
        fgets(message, sizeof(message), stdin);

        if (strcmp(message, "exit\n") == 0) {
            break;
        }

        // Enviar mensaje al servidor
        send(clientSocket, message, strlen(message), 0);

        // Recibir respuesta del servidor
        recv(clientSocket, message, sizeof(message), 0);
        message[strlen(message)] = '\0';
        printf("Respuesta del servidor: %s\n", message);
    }

    // Cerrar el socket del cliente
    closesocket(clientSocket);

    // Limpiar Winsock
    WSACleanup();

    return 0;
}
