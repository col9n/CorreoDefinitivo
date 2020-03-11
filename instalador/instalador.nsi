# Nombre del instalador
Name "ClienteCorreo"

# El nombre del instalador
OutFile "ClienteCorreo.exe"

# Configuramos la ruta por defecto donde se instala
InstallDir $DESKTOP\ClienteCorreo

# Pedimos permisos para Windows
RequestExecutionLevel admin

# Pantallas que hay que mostrar del instalador
Page directory
Page instfiles

# Cambiar el idioma
!include "MUI.nsh"
!insertmacro MUI_LANGUAGE "Spanish"

# Seccion principal
Section


# Establecemos la ruta de instalacion al directorio de instalacion
SetOutPath $INSTDIR

#donde tengo los jar y crear las carpeta del help y los informes
File /r "..\out\artifacts\correo_jar\*"
SetOutPath $INSTDIR\help
File  "..\help\articles.zip"
SetOutPath $INSTDIR\reportes
File /r "..\reportes\*"

#Correo persistencia datos
SetOutPath $INSTDIR\datos
File  "..\datos\datosCorreo.txt"
File  "..\datos\datostareas.txt"

#donde tengo javafx y donde tengo el java runtime
SetOutPath $INSTDIR\javafx
File /r "C:\Users\Usuario\Desktop\javafx-sdk-13\*"
SetOutPath $INSTDIR\java-runtime
File /r "C:\Program Files\Java\jdk-13.0.2\bin\java-runtime\*"

SetOutPath $INSTDIR
CreateShortCut \
 `$DESKTOP\ClienteCorreo.lnk` \
 `$INSTDIR\java-runtime\bin\java.exe` \
 `--module-path "$INSTDIR\javafx\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web,javafx.base --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED -jar "$INSTDIR\Correo.jar"` \


# Creamos el desinstalador
writeUninstaller "$INSTDIR\uninstall.exe"

# Creamos un acceso directo apuntando al desinstalador
createShortCut "$DESKTOP\Desinstalar.lnk" "$INSTDIR\uninstall.exe"

# Fin de la seccion

SectionEnd

# Seccion del desintalador
Section "uninstall"

delete "$INSTDIR\uninstall.exe"

RmDir /r "$INSTDIR"


# Borramos el acceso directo del menu de inicio
delete "$DESKTOP\Desinstalar.lnk"
delete "$DESKTOP\ClienteCorreo.lnk"

# Fin de la seccion del desinstalador
SectionEnd
