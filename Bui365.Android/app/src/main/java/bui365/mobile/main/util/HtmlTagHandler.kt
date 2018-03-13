package bui365.mobile.main.util

import android.text.Editable
import android.text.Html
import android.text.Layout
import android.text.Spannable
import android.text.style.AlignmentSpan
import android.text.style.BulletSpan
import android.text.style.LeadingMarginSpan
import android.text.style.TypefaceSpan
import org.xml.sax.XMLReader
import java.util.*


class HtmlTagHandler : Html.TagHandler {
    private var mListItemCount = 0
    private val mListParents = Vector<String>()

    private class Code

    private class Center

    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
        if (opening) {
            // opening tag
            if (tag.equals("ul", ignoreCase = true) || tag.equals("ol", ignoreCase = true) || tag.equals("dd", ignoreCase = true)) {
                mListParents.add(tag)
                mListItemCount = 0
            } else if (tag.equals("code", ignoreCase = true)) {
                start(output, Code())
            } else if (tag.equals("center", ignoreCase = true)) {
                start(output, Center())
            }
        } else {
            // closing tag
            if (tag.equals("ul", ignoreCase = true) || tag.equals("ol", ignoreCase = true) || tag.equals("dd", ignoreCase = true)) {
                mListParents.remove(tag)
                mListItemCount = 0
            } else if (tag.equals("li", ignoreCase = true)) {
                handleListTag(output)
            } else if (tag.equals("code", ignoreCase = true)) {
                end(output, Code::class.java, TypefaceSpan("monospace"), false)
            } else if (tag.equals("center", ignoreCase = true)) {
                end(output, Center::class.java, AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), true)
            }
        }
    }

    /**
     * Mark the opening tag by using private classes
     *
     * @param output
     * @param mark
     */
    private fun start(output: Editable, mark: Any) {
        val len = output.length
        output.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK)
    }

    private fun end(output: Editable, kind: Class<*>, repl: Any, paragraphStyle: Boolean) {
        val obj = getLast(output, kind)
        // start of the tag
        val where = output.getSpanStart(obj)
        // end of the tag
        var len = output.length

        output.removeSpan(obj)

        if (where != len) {
            // paragraph styles like AlignmentSpan need to end with a new line!
            if (paragraphStyle) {
                output.append("\n")
                len++
            }
            output.setSpan(repl, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

    }

    /**
     * Get last marked position of a specific tag kind (private class)
     *
     * @param text
     * @param kind
     * @return
     */
    private fun getLast(text: Editable, kind: Class<*>): Any? {
        val objs = text.getSpans(0, text.length, kind)
        if (objs.isEmpty()) {
            return null
        } else {
            for (i in objs.size downTo 1) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1]
                }
            }
            return null
        }
    }

    private fun handleListTag(output: Editable) {
        if (mListParents.lastElement() == "ul") {
            output.append("\n")
            val split = output.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val lastIndex = split.size - 1
            val start = output.length - split[lastIndex].length - 1
            output.setSpan(BulletSpan(15 * mListParents.size), start, output.length, 0)
        } else if (mListParents.lastElement() == "ol") {
            mListItemCount++

            output.append("\n")
            val split = output.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val lastIndex = split.size - 1
            val start = output.length - split[lastIndex].length - 1
            output.insert(start, mListItemCount.toString() + ". ")
            output.setSpan(LeadingMarginSpan.Standard(15 * mListParents.size), start, output.length, 0)
        }
    }
}
