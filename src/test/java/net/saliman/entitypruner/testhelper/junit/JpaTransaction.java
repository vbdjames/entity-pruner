package net.saliman.entitypruner.testhelper.junit;

import net.saliman.entitypruner.testhelper.DatabaseType;

import javax.ejb.Local;


/**
 * Interface for a Stateless bean that provides a transactional context for
 * Out of container unit tests, since unit tests can't obtain transactions 
 * directly.  The default behavior of a transaction is for it to always 
 * rollback.  If, for some reason, a transaction should commit, use the
 * {@link #setDefaultRollback(boolean)} method.
 * 
 * @author Steven C. Saliman
 */
@Local
public interface JpaTransaction {
	/**
	 * @return the database type in use.
	 */
	public DatabaseType getDatabaseType();

	/**
	 * Set the database type for this transaction.  Sometimes, we need to do
	 * things a little differently based on the underlying database. For 
	 * example, Oracle needs special handling of bind variables in a native
	 * query.
	 * @param databaseType the database type to use.
	 */
	public void setDatabaseType(DatabaseType databaseType);

    /**
     * @return the defaultRollback
     */
    public boolean isDefaultRollback();

    /**
     * Sets whether or not transactions should be rolled back after each
     * test.  The default is <code>true</code>, meaning that transactions will
     * rollback after each test.
     * @param defaultRollback the defaultRollback to set
     */
    public void setDefaultRollback(boolean defaultRollback);

    /**
     * Convenience method that runs the given native SQL in the test's current
     * transaction.  This method is very handy for setting up the test data
     * a test will operate on.  This method won't work unless it is inside
     * a {@link Transactable} passed to {@link #runInTransaction(Transactable)}.
     * @param sql The native query to run
     * @param args Any parameters to the query.
     * @return the number of rows affected by the SQL.
     * @throws Exception if anything goes wrong.
     */
    public int executeUpdate(String sql, Object... args) throws Exception;

    /**
     * Convenience method to count the number of rows in a table. This 
     * method won't work unless it is inside a {@link Transactable} passed to 
     * {@link #runInTransaction(Transactable)}.
     * @param tableName the name of the table to query
     * @return the number of rows in the given table.
     */
    public int countRowsInTable(String tableName);
    
    /**
     * Creates a new JPA transaction and Runs the code in the given
     * {@link Transactable}. 
     * @param transactable the code to run
     * @throws Exception whatever exception the runnable throws. This method
     * will rollback even checked exceptions.
     */
    public void runInTransaction(Transactable transactable) throws Exception;

}