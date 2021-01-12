package net.kozinaki.kt.jooq

import net.kozinaki.java.jooq.generated.tables.Author.AUTHOR
import net.kozinaki.java.jooq.generated.tables.Book.BOOK
import org.jooq.Record
import org.jooq.impl.*;
import org.jooq.impl.DSL.*
import org.jooq.SQLDialect
import java.sql.Connection
import java.sql.DriverManager


fun main(args: Array<String>) {
    val connection: Connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jooq", "postgres", "postgres");
    val create = DSL.using(connection, SQLDialect.POSTGRES);
    val books: List<Record> = create.select(BOOK.fields().toCollection(ArrayList()))
        .from(BOOK)
        .join(AUTHOR)
        .on(field(BOOK.AUTHOR_ID).equal(field(AUTHOR.ID)))
        //.where(field("BOOK.PUBLISHED_IN").eq(1948))
        .toList();
    books.forEach { println(it[BOOK.TITLE] + ' ' + it[BOOK.AUTHOR_ID] + ' ' + it[BOOK.LANGUAGE_ID] + ' ' + it[BOOK.PUBLISHED_IN]) }
}