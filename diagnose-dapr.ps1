# Script de diagnóstico para servicios DAPR
# Ubicación: C:\Users\jmedrano\Desktop\demo-dapr\dapr

Write-Host "🔍 DIAGNÓSTICO DE SERVICIOS DAPR" -ForegroundColor Cyan
Write-Host "=================================" -ForegroundColor Cyan

# Verificar si DAPR está instalado
Write-Host "`n1. Verificando instalación de DAPR..." -ForegroundColor Yellow
try {
    $daprVersion = dapr --version
    Write-Host "✅ DAPR está instalado: $daprVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ DAPR no está instalado" -ForegroundColor Red
    exit 1
}

# Verificar procesos DAPR corriendo
Write-Host "`n2. Verificando procesos DAPR activos..." -ForegroundColor Yellow
$daprProcesses = Get-Process -Name "dapr" -ErrorAction SilentlyContinue
if ($daprProcesses) {
    Write-Host "✅ Procesos DAPR encontrados:" -ForegroundColor Green
    $daprProcesses | ForEach-Object {
        Write-Host "   - PID: $($_.Id), Nombre: $($_.ProcessName)" -ForegroundColor White
    }
} else {
    Write-Host "❌ No hay procesos DAPR corriendo" -ForegroundColor Red
}

# Verificar puertos en uso
Write-Host "`n3. Verificando puertos DAPR..." -ForegroundColor Yellow
$ports = @(3500, 3501, 3502, 50001, 50002, 50003)
foreach ($port in $ports) {
    $connection = Test-NetConnection -ComputerName "localhost" -Port $port -InformationLevel Quiet -WarningAction SilentlyContinue
    if ($connection) {
        Write-Host "✅ Puerto $port está en uso" -ForegroundColor Green
    } else {
        Write-Host "❌ Puerto $port no está en uso" -ForegroundColor Red
    }
}

# Verificar servicios de aplicación
Write-Host "`n4. Verificando servicios de aplicación..." -ForegroundColor Yellow
$appPorts = @(8086, 8088)
foreach ($port in $appPorts) {
    $connection = Test-NetConnection -ComputerName "localhost" -Port $port -InformationLevel Quiet -WarningAction SilentlyContinue
    if ($connection) {
        Write-Host "✅ Puerto $port (aplicación) está en uso" -ForegroundColor Green
    } else {
        Write-Host "❌ Puerto $port (aplicación) no está en uso" -ForegroundColor Red
    }
}

# Verificar configuración de variables de entorno
Write-Host "`n5. Verificando variables de entorno DAPR..." -ForegroundColor Yellow
$envVars = @("DAPR_HTTP_PORT", "DAPR_GRPC_PORT")
foreach ($var in $envVars) {
    $value = [Environment]::GetEnvironmentVariable($var)
    if ($value) {
        Write-Host "✅ $var = $value" -ForegroundColor Green
    } else {
        Write-Host "❌ $var no está configurada" -ForegroundColor Red
    }
}

Write-Host "`n📋 RESUMEN DEL DIAGNÓSTICO" -ForegroundColor Cyan
Write-Host "=========================" -ForegroundColor Cyan
Write-Host "Si hay puertos no en uso, ejecuta: .\start-services.ps1" -ForegroundColor Yellow
Write-Host "Si hay problemas de configuración, revisa los archivos YAML" -ForegroundColor Yellow 