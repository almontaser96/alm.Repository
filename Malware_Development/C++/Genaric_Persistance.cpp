#include<windows.h>
#include<iostream>
#include<filesystem>

namespace fs =std::filesystem;

std::string getUserName(){
    char user[256];
    DWORD size = sizeof(user);
    GetUserNameA(user,&size);
    return std::string(user);
}

int main(){
fs::path srcDir = "C:\\Users\\"+getUserName()+"\\Downloads";
fs::path dstDir = "C:\\Users\\"+getUserName()+"";
std::string fileName = "Binary.exe";
fs::path sourceFile=srcDir/fileName;
fs::path destinationFile=dstDir/fileName;

fs::copy_file(sourceFile,destinationFile,fs::copy_options::overwrite_existing);
std::cout<<srcDir<<std::endl;
std::cout<<dstDir<<std::endl;
std::cout<<sourceFile<<std::endl;
std::cout<<destinationFile<<std::endl;
int x;
std::cin>>x;
return 0;
};

