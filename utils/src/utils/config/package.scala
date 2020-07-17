package utils

import org.bukkit.configuration.file.FileConfiguration

package object config {
  implicit class StringExtensions(val string: String) extends AnyVal {
    def resolveLong(implicit config: FileConfiguration): Long = config.getLong(string)

    def resolveBoolean(implicit config: FileConfiguration): Boolean = config.getBoolean(string)

    def resolveOptionalLong(implicit config: FileConfiguration): Option[Long] =
      string.resolveLong match {
        case 0 | -1 => None
        case number => Some(number)
      }
  }
}
