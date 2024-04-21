#include <windows.h>
#include<wchar.h>
int main() {
    LPCWSTR taskName = L"MyScheduledTask";
    LPCWSTR exePath = L"C:\\Path\\To\\Your\\Executable.exe";
    LPCWSTR arguments = L""; // Add your executable's arguments if needed

    // Construct the command string
    wchar_t command[256];
    swprintf_s(command, L"schtasks /create /tn \"%s\" /tr \"%s\" /sc minute /mo 1", taskName, exePath);

    // Run the command
    _wsystem(command);

    return 0;
}