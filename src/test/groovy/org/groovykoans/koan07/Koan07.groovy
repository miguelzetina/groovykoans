/*
 * Copyright (c) 2012-2014 nadavc <https://twitter.com/nadavc>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the WTFPL, Version 2, as published by Sam Hocevar.
 * See the COPYING file for more details.
 */

package org.groovykoans.koan07

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Koan07 - Regular Expressions
 *
 * Resource list:
 *   * http://www.vogella.com/articles/JavaRegularExpressions/article.html
 *   * http://docs.groovy-lang.org/latest/html/documentation/index.html#all-strings
 *   * http://docs.groovy-lang.org/latest/html/documentation/index.html#_regular_expression_operators
 *   * http://naleid.com/blog/2009/04/07/groovy-161-released-with-new-find-and-findall-regexp-methods-on-string/
 *   * http://www.ngdc.noaa.gov/wiki/index.php/Regular_Expressions_in_Groovy#The_eXtended_Pattern_Match_Flag_.28x.29
 */
class Koan07 extends GroovyTestCase {

    void test01_SimpleRegularExpression() {
        // First we must understand regular expressions. There's a nice tutorial at
        // http://www.vogella.com/articles/JavaRegularExpressions/article.html

        // Using your knowledge of regular expressions, create a regexp string that gets all the technologies
        // that begin with 'G' and end with either 'e' or 's'
        def technologies = ['Grails', 'Gradle', '.NET', 'Python', 'Groovy']
        def regexp
        // ------------ START EDITING HERE ----------------------

        regexp = '^G.*[e|s]$'

        // ------------ STOP EDITING HERE  ----------------------
        def result = technologies.findAll { it ==~ regexp }

        assert result == ['Grails', 'Gradle']
    }

    void test02_MultilineStrings() {
        // With Groovy it's possible to declare multiline strings using either ''' or """ (Multiline GString).
        // More info at http://docs.groovy-lang.org/latest/html/documentation/index.html#_triple_double_quoted_string

        String signs = '+, \\, and others'

        // Create the string below with Groovy
        String javaString = "In Java a multiline string\n" +
                "requires using special signs such as " + signs + "\n" +
                "and can become difficult to maintain"
        String groovyString
        // ------------ START EDITING HERE ----------------------

        groovyString = """In Java a multiline string
requires using special signs such as ${signs}
and can become difficult to maintain"""

        // ------------ STOP EDITING HERE  ----------------------
        assert groovyString == javaString
    }

    void test03_SlashyStrings() {
        // The other type of String declaration is called Slashy strings. They're especially useful for
        // regular expressions. Read about them at
        // http://docs.groovy-lang.org/latest/html/documentation/index.html#_slashy_string

        // Suppose we have the following text:
        def text = '''|Item          # Sold  Leftover
                      |Xbox          2000    10
                      |Playstation   100     5
                      |Wii           5       1000'''.stripMargin()

        // If we wanted to grab the number of leftover items with a regular expression in Java, we would do this:
        int javaSum = 0;
        String javaRegExp = "(?sm)(.*?)\\s+(\\d+)\\s+(\\d+)"
        Matcher javaMatcher = Pattern.compile(javaRegExp).matcher(text);
        while (javaMatcher.find()) {
            javaSum += Integer.valueOf(javaMatcher.group(3));
        }

        // Slashy strings don't need escape backslashes in regular expressions. Translate the above regexp to
        // a Slashy string regexp
        def groovyRegExp
        // ------------ START EDITING HERE ----------------------

        groovyRegExp = /(?sm)(.*?)\s+(\d+)\s+(\d+)/

        // ------------ STOP EDITING HERE  ----------------------
        def matcher = text =~ groovyRegExp
        def groovySum = matcher.collect { it[3].toInteger() }.sum()

        // ^^ Look how much more concise the Groovy code is! There's even a shorter version ahead...
        assert groovySum == javaSum
    }

    void test04_SpecialRegexpOperators() {
        // Groovy is very useful for text manipulation, and as such regular expressions get 3 special operators:
        // ~str (tilde) : creates a Pattern object from a string. Equivalent to Pattern.compile(str)
        // str =~ pattern : creates a Matcher from a regex and a string. Same as Pattern.compile(pattern).matcher(str)
        // str ==~ pattern : returns a boolean if pattern matches str. Same as Pattern.matches(pattern, str)
        // More docs at http://docs.groovy-lang.org/latest/html/documentation/index.html#_regular_expression_operators

        // This is how a Pattern object is defined in Java for an arbitrary phone number format
        // (xxx xxxx or xxxxxxx or xxx,xxxx)
        Pattern patternInJava = Pattern.compile("\\d{3}([,\\s])?\\d{4}");

        // Create the same Pattern object in Groovy
        def patternInGroovy
        // ------------ START EDITING HERE ----------------------

        patternInGroovy = ~/\d{3}([,\s])?\d{4}/

        // ------------ STOP EDITING HERE  ----------------------
        assert patternInGroovy instanceof Pattern
        assertEquals(patternInJava.pattern(), patternInGroovy.pattern())

        // Once you have matches using the Groovy Matcher, you are able to iterate through them using the each() method.
        // Create a matcher and get the first names from the source text
        def names = 'John Lennon, Paul McCartney, George Harrison, Ringo Starr'
        def firstNamesList = []
        // ------------ START EDITING HERE ----------------------

        def matcher = names =~ /(\w+)\s(\w+)/
        matcher.each { match, firstName, lastName ->
            firstNamesList << firstName
        }

        // ------------ STOP EDITING HERE  ----------------------
        assert firstNamesList == ['John', 'Paul', 'George', 'Ringo']

        // And finally, using the ==~ operator, check if the the following number is a valid Visa card:
        // You can get the regular expression for Visas here: http://www.regular-expressions.info/creditcard.html
        def number = '4927856234092'
        boolean isNumberValid = false
        // ------------ START EDITING HERE ----------------------

        isNumberValid = number ==~ /^4[0-9]{12}(?:[0-9]{3})?$/

        // ------------ STOP EDITING HERE  ----------------------
        assert isNumberValid, 'Visa number should be valid!'
    }

    void test05_ReplaceAllWithClosure() {
        // If your regular expression needs some "business logic" for search & replace, you can put it into Closures.
        // Reference:
        // http://naleid.com/blog/2009/04/07/groovy-161-released-with-new-find-and-findall-regexp-methods-on-string/

        // Using your newly acquired knowledge, create a regex that iterates all the words and replaces them using the
        // supplied dictionary.  E.g. 'this town is mad!' -> 'this ciudad is mad!'
        def dictionary = ['town': 'ciudad', 'man': 'hombre', 'life': 'vida']
        def song = '''|In the town where I was born
                      |Lived a man who sailed to sea
                      |And he told us of his life
                      |In the land of submarines'''.stripMargin()
        def result
        // ------------ START EDITING HERE ----------------------

        result = song.replaceAll(/\w+/) { dictionary[it] ?: it }

        // ------------ STOP EDITING HERE  ----------------------

        def expected = '''|In the ciudad where I was born
                          |Lived a hombre who sailed to sea
                          |And he told us of his vida
                          |In the land of submarines'''.stripMargin()

        assert result == expected
    }

    void test06_MultilineRegexWithComments() {
        // Regular expression can become lengthy and hard to read. Groovy solves this by adding a special
        // "extended" (x) flag that ignores newlines and spaces. Read about it here:
        // http://www.ngdc.noaa.gov/wiki/index.php/Regular_Expressions_in_Groovy#The_eXtended_Pattern_Match_Flag_.28x.29

        // Let's take the text from the exercise above:
        def text = '''|Item          # Sold  Leftover
                      |Xbox          2000    10
                      |Playstation   100     5
                      |Wii           5       1000'''.stripMargin()

        // create the same regular expression to sum the total leftovers, but this time document the regex
        String regexp
        // ------------ START EDITING HERE ----------------------

        // regexp = /(?sm)(.*?)\s+(\d+)\s+(\d+)/
        regexp = /(?smx)#
                 (.*?)  #
                 \s+    #
                 (\d+)  #
                 \s+    #
                 (\d+)  #/

        // ------------ STOP EDITING HERE  ----------------------
        def sum = text.findAll(regexp) { it[3].toInteger() }.sum()
        // ^^ This is even more concise than the previous example! Choose the one you feel most comfortable with.

        assert sum == 1015
        def options = regexp.find(/\(\?[^\)]*\)/)
        assert options.contains('x'), 'A commented regex must use the x flag'
        assert regexp.contains('#'), 'Comments can be inserted using the # character'
    }


}
