package cz.vhromada.book.stub

interface BookcaseStub {

    /**
     * Checks if stub has any unverified interaction.
     */
    fun verifyNoMoreInteractions()

    /**
     * Verifies that no interactions happened.
     */
    fun verifyZeroInteractions()

}
