# Source this from bash. Includes helpful commands for development.

OTM_DIR="${OTM_DIR:-$HOME/code/overtime}"

OTM_SERVICE_DIR="$OTM_DIR/webmvc"
OTM_DATABASE="overtime"

# Create (or recreate) the Postgres database and run Liquibase. The database is owned by the current user.
function otm-create-schema() {
    otm-create-db || return
    otm-update-schema
}

# Create (or recreate) the Postgres database, owned by the current user.
function otm-create-db() {
    dropdb --if-exists --echo "$OTM_DATABASE" || return
    createdb --echo "$OTM_DATABASE" || return
}

# Run Liquibase update
function otm-update-schema() {
    otm-exec-in-dir "$OTM_SERVICE_DIR" liquibase update
}

# Executes a command in a given directory
# $1 - directory
# $* - command
function otm-exec-in-dir() {
    local dir=$1
    shift
    pushd "$dir" >/dev/null || return
    "$@"
    local result=$?
    popd >/dev/null || return
    return $result
}
