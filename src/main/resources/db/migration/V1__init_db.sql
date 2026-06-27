-- ============================================================================
-- 1. СОЗДАНИЕ ENUM-ТИПОВ
-- ============================================================================
CREATE TYPE device_type AS ENUM ('SERVER', 'ATM', 'CHECKPOINT');
CREATE TYPE status_type AS ENUM ('GOOD', 'BAD', 'UNKNOWN');

-- Типы для логов и алертов вместо текстового описания (enum/string)
CREATE TYPE ping_result_type AS ENUM ('SUCCESS', 'TIMEOUT', 'FAILED');
CREATE TYPE ping_kind_type AS ENUM ('ICMP', 'HTTP', 'TCP');

CREATE TYPE alert_severity_type AS ENUM ('INFO', 'WARNING', 'CRITICAL');
CREATE TYPE alert_status_type AS ENUM ('OPEN', 'ACKNOWLEDGED', 'RESOLVED');

-- ============================================================================
-- 2. СОЗДАНИЕ ТАБЛИЦ (От независимых к зависимым)
-- ============================================================================

-- Вспомогательная таблица пользователей (нужна для связи closed_by в alerts)
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(100) NOT NULL UNIQUE
);

-- Группы устройств (родительская таблица для devices)
CREATE TABLE device_groups (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               name VARCHAR(255),
                               code VARCHAR(255) UNIQUE,
                               description TEXT,
                               location VARCHAR(255),
                               is_active BOOLEAN DEFAULT true,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Устройства
CREATE TABLE devices (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         name VARCHAR(255),
                         ip_address VARCHAR(255) UNIQUE,
                         device device_type DEFAULT 'ATM',
                         status status_type DEFAULT 'UNKNOWN',

    -- ИСПРАВЛЕНО: изменен на UUID, так как в device_groups.id используется UUID
                         group_id UUID REFERENCES device_groups(id) ON DELETE SET NULL,

                         is_active BOOLEAN DEFAULT true,
                         description TEXT,
                         last_ping_at TIMESTAMP,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Логи пинга
CREATE TABLE ping_logs (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- ИСПРАВЛЕНО: тип изменен на UUID для соответствия первичному ключу devices
                           device_id UUID REFERENCES devices(id) ON DELETE SET NULL,

                           ping_type ping_kind_type NOT NULL,      -- Обязательное поле (required)
                           result ping_result_type NOT NULL,        -- Обязательное поле (required)
                           response_time_ms INTEGER,                -- По умолчанию может быть NULL (nullable)
                           error_message TEXT,                      -- По умолчанию может быть NULL (nullable)
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Алерты
CREATE TABLE alerts (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Выбран UUID для консистентности системы

                        device_id UUID NOT NULL REFERENCES devices(id) ON DELETE CASCADE, -- Обязательное (required)
                        alert_type VARCHAR(255) NOT NULL,                                 -- Обязательное
                        severity alert_severity_type NOT NULL,                            -- Обязательное
                        status alert_status_type NOT NULL DEFAULT 'OPEN',                 -- Обязательное
                        message TEXT NOT NULL,                                            -- Обязательное

                        opened_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        closed_at TIMESTAMP,                                              -- Может быть NULL (nullable)
                        closed_by UUID REFERENCES users(id) ON DELETE SET NULL            -- Может быть NULL (nullable)
);