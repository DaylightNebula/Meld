package net.benwoodworth.knbt.internal

import okio.*
import java.util.zip.Deflater
import java.util.zip.Inflater

internal fun Source.asGzipSource(): Source = this.gzip()

internal fun Sink.asGzipSink(level: Int): Sink = this.gzip().apply { deflater.setLevel(level) }

internal fun Source.asZlibSource(): Source = inflate(Inflater())

internal fun Sink.asZlibSink(level: Int): Sink = deflate(Deflater(level, false))
