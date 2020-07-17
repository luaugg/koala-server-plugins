package utils

import org.bukkit.configuration.file.FileConfiguration

package object config {
  implicit class StringExtensions(val string: String) extends AnyVal {
    def resolveInt(implicit config: FileConfiguration): Int = config.getInt(string)

    def resolveOptionalInt(implicit config: FileConfiguration): Option[Int] =
      string.resolveInt match {
        case 0 | -1 => None
        case number => Some(number)
      }

    def resolveBoolean(implicit config: FileConfiguration): Boolean = config.getBoolean(string)
  }
}
