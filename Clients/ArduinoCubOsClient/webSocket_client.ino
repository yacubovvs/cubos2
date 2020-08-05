#ifdef connector_wifi_socket_server

    #include <WiFi.h>
    #include <WebSocketClient.h>
    
    WebSocketClient webSocketClient;
    WiFiClient client;
    
    #define server_wifi_socket_port 8000
    #define server_wifi_socket_ip "192.168.1.38"

    void socket_server_setup(){
        WiFi.begin(ssid, password);
        while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        drawString("Connecting...", 10, 10, 255,255,255);
        }
        drawString("Connected " + WiFi.localIP().toString(), 10, 20, 255,255,255);

    }

    void socket_server_loop(){
        
    }

#endif
