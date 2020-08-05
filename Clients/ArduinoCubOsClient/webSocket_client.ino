#ifdef connector_wifi_socket_server

    #include <WiFi.h>
    #include <WebSocketsServer.h>

    const char* ssid = "DIR-615";
    const char* password = "tsdurovo6200";
    #define server_wifi_socket_port 80
    #define server_wifi_socket_ip "10.0.0.153"

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
