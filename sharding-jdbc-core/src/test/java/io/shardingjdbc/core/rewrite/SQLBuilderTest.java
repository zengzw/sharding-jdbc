/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingjdbc.core.rewrite;

import io.shardingjdbc.core.rewrite.placeholder.TablePlaceholder;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class SQLBuilderTest {
    
    @Test
    public void assertAppendLiteralsOnly() {
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.appendLiterals("SELECT ");
        sqlBuilder.appendLiterals("table_x");
        sqlBuilder.appendLiterals(".id");
        sqlBuilder.appendLiterals(" FROM ");
        sqlBuilder.appendLiterals("table_x");
        assertThat(sqlBuilder.toSQL(Collections.<String, String>emptyMap(), null), is("SELECT table_x.id FROM table_x"));
    }
    
    @Test
    public void assertAppendTableWithoutTableToken() {
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.appendLiterals("SELECT ");
        sqlBuilder.appendPlaceholder(new TablePlaceholder("table_x"));
        sqlBuilder.appendLiterals(".id");
        sqlBuilder.appendLiterals(" FROM ");
        sqlBuilder.appendPlaceholder(new TablePlaceholder("table_x"));
        assertThat(sqlBuilder.toSQL(Collections.<String, String>emptyMap(), null), is("SELECT table_x.id FROM table_x"));
    }
    
    @Test
    public void assertAppendTableWithTableToken() {
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.appendLiterals("SELECT ");
        sqlBuilder.appendPlaceholder(new TablePlaceholder("table_x"));
        sqlBuilder.appendLiterals(".id");
        sqlBuilder.appendLiterals(" FROM ");
        sqlBuilder.appendPlaceholder(new TablePlaceholder("table_x"));
        Map<String, String> tableTokens = new HashMap<>(1, 1);
        tableTokens.put("table_x", "table_x_1");
        assertThat(sqlBuilder.toSQL(tableTokens, null), is("SELECT table_x_1.id FROM table_x_1"));
    }
}
