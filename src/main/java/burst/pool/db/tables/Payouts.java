/*
 * This file is generated by jOOQ.
 */
package burst.pool.db.tables;


import burst.pool.db.DefaultSchema;
import burst.pool.db.Indexes;
import burst.pool.db.Keys;
import burst.pool.db.tables.records.PayoutsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Payouts extends TableImpl<PayoutsRecord> {

    private static final long serialVersionUID = 1122049568;

    /**
     * The reference instance of <code>payouts</code>
     */
    public static final Payouts PAYOUTS = new Payouts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PayoutsRecord> getRecordType() {
        return PayoutsRecord.class;
    }

    /**
     * The column <code>payouts.db_id</code>.
     */
    public final TableField<PayoutsRecord, Long> DB_ID = createField("db_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>payouts.transaction_id</code>.
     */
    public final TableField<PayoutsRecord, Long> TRANSACTION_ID = createField("transaction_id", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>payouts.sender_public_key</code>.
     */
    public final TableField<PayoutsRecord, byte[]> SENDER_PUBLIC_KEY = createField("sender_public_key", org.jooq.impl.SQLDataType.BINARY(32).defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.BINARY)), this, "");

    /**
     * The column <code>payouts.fee</code>.
     */
    public final TableField<PayoutsRecord, Long> FEE = createField("fee", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>payouts.deadline</code>.
     */
    public final TableField<PayoutsRecord, Long> DEADLINE = createField("deadline", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>payouts.attachment</code>.
     */
    public final TableField<PayoutsRecord, byte[]> ATTACHMENT = createField("attachment", org.jooq.impl.SQLDataType.BLOB.defaultValue(org.jooq.impl.DSL.field("NULL", org.jooq.impl.SQLDataType.BLOB)), this, "");

    /**
     * Create a <code>payouts</code> table reference
     */
    public Payouts() {
        this(DSL.name("payouts"), null);
    }

    /**
     * Create an aliased <code>payouts</code> table reference
     */
    public Payouts(String alias) {
        this(DSL.name(alias), PAYOUTS);
    }

    /**
     * Create an aliased <code>payouts</code> table reference
     */
    public Payouts(Name alias) {
        this(alias, PAYOUTS);
    }

    private Payouts(Name alias, Table<PayoutsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Payouts(Name alias, Table<PayoutsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Payouts(Table<O> child, ForeignKey<O, PayoutsRecord> key) {
        super(child, key, PAYOUTS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PAYOUTS_PAYOUTS_INDEX, Indexes.PAYOUTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PayoutsRecord, Long> getIdentity() {
        return Keys.IDENTITY_PAYOUTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PayoutsRecord> getPrimaryKey() {
        return Keys.KEY_PAYOUTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PayoutsRecord>> getKeys() {
        return Arrays.<UniqueKey<PayoutsRecord>>asList(Keys.KEY_PAYOUTS_PRIMARY, Keys.KEY_PAYOUTS_PAYOUTS_INDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Payouts as(String alias) {
        return new Payouts(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Payouts as(Name alias) {
        return new Payouts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Payouts rename(String name) {
        return new Payouts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Payouts rename(Name name) {
        return new Payouts(name, null);
    }
}
