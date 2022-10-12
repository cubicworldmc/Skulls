package ca.tweetzy.skulls.feather.files.comments.format;


import ca.tweetzy.skulls.feather.files.configuration.comments.CommentType;
import ca.tweetzy.skulls.feather.files.configuration.comments.KeyTree;
import ca.tweetzy.skulls.feather.files.utils.StringUtils;

/**
 * {@link YamlCommentFormat#BLANK_LINE} formatter
 */
public class BlankLineYamlCommentFormatter extends YamlCommentFormatter {

    public BlankLineYamlCommentFormatter() {
        this(new YamlCommentFormatterConfiguration());
    }

    public BlankLineYamlCommentFormatter(final YamlCommentFormatterConfiguration blockFormatter) {
        this(blockFormatter, new YamlSideCommentFormatterConfiguration());
    }

    public BlankLineYamlCommentFormatter(final YamlCommentFormatterConfiguration blockFormatter, final YamlSideCommentFormatterConfiguration sideFormatter) {
        super(blockFormatter, sideFormatter);
        this.stripPrefix(true).trim(false);
        blockFormatter.prefix('\n' + blockFormatter.prefixFirst(), blockFormatter.prefixMultiline());
    }

    @Override
    public String dump(final String comment, final CommentType type, final KeyTree.Node node) {
        if (type == CommentType.SIDE) {
            final String defaultPrefixFirst = sideFormatter.prefixFirst();
            final String blankLineSideFirstPrefix = '\n' + StringUtils.stripIndentation(defaultPrefixFirst);
            sideFormatter.prefix(blankLineSideFirstPrefix, sideFormatter.prefixMultiline());
            final String dump = super.dump(comment, type, node);
            sideFormatter.prefix(defaultPrefixFirst, sideFormatter.prefixMultiline());
            return dump;
        }
        return super.dump(comment, type, node);
    }

}
