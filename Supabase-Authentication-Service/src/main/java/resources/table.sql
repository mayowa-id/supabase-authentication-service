-- Create users table
create table users (
    id uuid references auth.users primary key,
    email text unique,
    full_name text,
    organization_id uuid,
    roles jsonb
);

-- Create organizations table
create table organizations (
    id uuid primary key default uuid_generate_v4(),
    name text,
    owner_id uuid references users(id)
);

-- Create organizations_users junction table
create table organizations_users (
    organization_id uuid references organizations(id),
    user_id uuid references users(id),
    role text,
    primary key (organization_id, user_id)
);