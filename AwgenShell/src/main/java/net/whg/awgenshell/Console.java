package net.whg.awgenshell;

public class Console implements CommandConsole
{
	public static final int LINE_COUNT = 512;

	private String[] _text;
	private int _scrollPos;

	public Console()
	{
		_text = new String[LINE_COUNT];
		_scrollPos = LINE_COUNT - 1;

		for (int i = 0; i < _text.length; i++)
			_text[i] = "";
	}

	@Override
	public void println(String text)
	{
		if (text == null)
			text = "";

		int lineStart = 0;
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) != '\n')
				continue;

			appendLine(text.substring(lineStart, i));
			lineStart = i + 1;
		}

		appendLine(text.substring(lineStart, text.length()));
	}

	private void appendLine(String text)
	{
		int newScroll = (_scrollPos + 1) % getLineCount();
		_scrollPos = newScroll;

		int line = (_scrollPos - 1 + getLineCount()) % getLineCount();
		setLine(line, text);
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < _text.length; i++)
			setLine(i, "");
	}

	@Override
	public String getLine(int index)
	{
		return _text[index];
	}

	@Override
	public int getLineCount()
	{
		return _text.length;
	}

	@Override
	public void setLine(int line, String text)
	{
		if (_text[line].equals(text))
			return;

		_text[line] = text;
	}

	@Override
	public int getScrollPos()
	{
		return _scrollPos;
	}
}
