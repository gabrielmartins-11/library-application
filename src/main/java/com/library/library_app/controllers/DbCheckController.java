package com.library.library_app.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbCheckController {
    private final JdbcTemplate jdbc;

    public DbCheckController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/db/tables")
    public List<String> tables() {
        return jdbc.queryForList("SHOW TABLES IN library_db", String.class);
    }

    @GetMapping("/db/schema")
    public List<TableInfo> schema() {
        List<String> tables = tables();
        return tables.stream().map(table -> {
            List<ColumnInfo> columns = jdbc.query(
                    "SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION",
                    new Object[]{"library_db", table},
                    (rs, rowNum) -> new ColumnInfo(
                            rs.getString("COLUMN_NAME"),
                            rs.getString("COLUMN_TYPE"),
                            rs.getString("IS_NULLABLE"),
                            rs.getString("COLUMN_DEFAULT")
                    )
            );
            return new TableInfo(table, columns);
        }).collect(Collectors.toList());
    }

    // Simple DTO for column metadata
    public static class ColumnInfo {
        private String name;
        private String type;
        private String isNullable;
        private String columnDefault;

        public ColumnInfo(String name, String type, String isNullable, String columnDefault) {
            this.name = name;
            this.type = type;
            this.isNullable = isNullable;
            this.columnDefault = columnDefault;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public String getIsNullable() { return isNullable; }
        public String getColumnDefault() { return columnDefault; }
    }

    // Simple DTO for table + columns
    public static class TableInfo {
        private String tableName;
        private List<ColumnInfo> columns;

        public TableInfo(String tableName, List<ColumnInfo> columns) {
            this.tableName = tableName;
            this.columns = columns;
        }

        public String getTableName() { return tableName; }
        public List<ColumnInfo> getColumns() { return columns; }
    }
}
