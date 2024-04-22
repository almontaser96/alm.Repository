#include <iostream>
#include <locale>
#include <codecvt>

std::wstring stringToWstring(const std::string& str) {
    std::wstring_convert<std::codecvt_utf8<wchar_t>> converter;
    return converter.from_bytes(str);
}

std::string wstringToString(const std::wstring& wstr) {
    std::wstring_convert<std::codecvt_utf8<wchar_t>> converter;
    return converter.to_bytes(wstr);
}

int main() {
    // Example usage
    std::string utf8String = "Hello, 你好, नमस्ते";
    
    // Convert std::string to wchar_t*
    std::wstring wideString = stringToWstring(utf8String);
    const wchar_t* wideCString = wideString.c_str();

    // Convert wchar_t* to std::string
    std::string convertedString = wstringToString(wideString);

    // Display results
    std::wcout << L"Wide String: " << wideCString << std::endl;
    std::cout << "Converted String: " << convertedString << std::endl;
int x;
std::cin>>x;
    return 0;
}