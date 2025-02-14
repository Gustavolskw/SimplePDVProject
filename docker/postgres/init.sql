DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'cardapioloja') THEN
CREATE DATABASE cardapioloja;
END IF;
END $$;
