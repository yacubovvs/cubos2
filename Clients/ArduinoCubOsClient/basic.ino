uint64_t bytes_to_value(byte byte0, byte byte1, byte byte2, byte byte3, byte byte4, byte byte5, byte byte6, byte byte7){
  return (byte7<<56)|(byte6<<48)|(byte5<<40)|(byte4<<32)|(byte3<<24)|(byte2<<16)|(byte1<<8)|byte0;
}

uint64_t bytes_to_value(byte byte0, byte byte1, byte byte2, byte byte3, byte byte4, byte byte5, byte byte6){
  return (byte6<<48)|(byte5<<40)|(byte4<<32)|(byte3<<24)|(byte2<<16)|(byte1<<8)|byte0;
}

uint64_t bytes_to_value(byte byte0, byte byte1, byte byte2, byte byte3, byte byte4, byte byte5){
  return (byte5<<40)|(byte4<<32)|(byte3<<24)|(byte2<<16)|(byte1<<8)|byte0;
}

uint64_t bytes_to_value(byte byte0, byte byte1, byte byte2, byte byte3, byte byte4){
  return (byte4<<32)|(byte3<<24)|(byte2<<16)|(byte1<<8)|byte0;
}

uint32_t bytes_to_value(byte byte0, byte byte1, byte byte2, byte byte3){
  return (byte3<<24)|(byte2<<16)|(byte1<<8)|byte0;
}

uint32_t bytes_to_value(byte byte0, byte byte1, byte byte2){
  return (byte2<<16)|(byte1<<8)|byte0;
}

uint16_t bytes_to_value(byte byte0, byte byte1){
  return (byte1<<8)|byte0;
}
