package woowacourse.movie.ui.moviedetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.domain.TicketTime
import woowacourse.movie.domain.TimesGenerator
import woowacourse.movie.ui.const.KEY_MOVIE
import woowacourse.movie.ui.const.KEY_TICKET
import woowacourse.movie.ui.ticket.MovieTicketActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity() {
    private var peopleCount = PeopleCount()
    private lateinit var dateSpinner: Spinner
    private lateinit var timeSpinner: Spinner
    private lateinit var timeSpinnerAdapter: ArrayAdapter<LocalTime>
    private val times = mutableListOf<LocalTime>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = getMovieFromIntent()

        setMovieInfo(movie)
        setDateSpinner(movie)
        setTimeSpinner()
        setPeopleCountController()
        setBookingButton(movie)

        loadSavedData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt(KEY_DATE_POSITION, dateSpinner.selectedItemPosition)
        outState.putInt(KEY_TIME_POSITION, timeSpinner.selectedItemPosition)
        outState.putInt(KEY_PEOPLE_COUNT, peopleCount.count)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    private fun getMovieFromIntent() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(KEY_MOVIE, Movie::class.java)
    } else {
        intent.getSerializableExtra(KEY_MOVIE)
    } as Movie

    private fun setMovieInfo(movie: Movie) {
        findViewById<ImageView>(R.id.detail_poster).setImageResource(movie.poster)
        findViewById<TextView>(R.id.detail_title).text = movie.title
        findViewById<TextView>(R.id.detail_date).text = movie.getScreenDate()
        findViewById<TextView>(R.id.detail_running_time).text = movie.getRunningTime()
        findViewById<TextView>(R.id.detail_description).text = movie.description
    }

    private fun Movie.getScreenDate(): String = "상영일: ${startDate.format()} ~ ${endDate.format()}"

    private fun LocalDate.format(): String = format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

    private fun Movie.getRunningTime(): String = "러닝타임: ${runningTime}분"

    private fun setDateSpinner(movie: Movie) {
        dateSpinner = findViewById(R.id.detail_date_spinner)
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movie.getDatesBetweenTwoDates()
        )
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateSpinner.adapter = dateSpinnerAdapter
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                timeSpinner.setSelection(0)
                times.clear()
                times.addAll(TimesGenerator.getTimesByDate(dateSpinner.selectedItem as LocalDate))
                timeSpinnerAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setTimeSpinner() {
        timeSpinner = findViewById(R.id.detail_time_spinner)
        times.addAll(TimesGenerator.getTimesByDate(dateSpinner.selectedItem as LocalDate))
        timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            times
        )
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeSpinnerAdapter
    }

    private fun setPeopleCountController() {
        val peopleCountView = findViewById<TextView>(R.id.detail_people_count)
        setPeopleCountView(peopleCountView)
        setMinusButton(peopleCountView)
        setPlusButton(peopleCountView)
    }

    private fun setPeopleCountView(peopleCountView: TextView) {
        peopleCountView.text = "${peopleCount.count}"
    }

    private fun setMinusButton(peopleCountView: TextView) {
        val minusButton = findViewById<Button>(R.id.detail_minus_button)
        minusButton.setOnClickListener {
            peopleCount = peopleCount.minusCount()
            setPeopleCountView(peopleCountView)
        }
    }

    private fun setPlusButton(peopleCountView: TextView) {
        val plusButton = findViewById<Button>(R.id.detail_plus_button)
        plusButton.setOnClickListener {
            peopleCount = peopleCount.plusCount()
            setPeopleCountView(peopleCountView)
        }
    }

    private fun setBookingButton(movie: Movie) {
        val bookingButton = findViewById<Button>(R.id.detail_booking_button)

        bookingButton.setOnClickListener {
            moveToTicketActivity(movie)
        }
    }

    private fun moveToTicketActivity(movie: Movie) {
        val ticket = MovieTicket(
            movie.title,
            TicketTime(
                LocalDateTime.of(
                    dateSpinner.selectedItem as LocalDate,
                    timeSpinner.selectedItem as LocalTime
                )
            ),
            peopleCount
        )

        val intent = Intent(this, MovieTicketActivity::class.java)
        intent.putExtra(KEY_TICKET, ticket)
        startActivity(intent)
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        val datePosition = savedInstanceState?.getInt(KEY_DATE_POSITION) ?: 0
        val timePosition = savedInstanceState?.getInt(KEY_TIME_POSITION) ?: 0
        val count = savedInstanceState?.getInt(KEY_PEOPLE_COUNT) ?: 1
        dateSpinner.setSelection(datePosition)
        timeSpinner.setSelection(timePosition)
        peopleCount = PeopleCount(count)
    }

    companion object {
        private const val KEY_DATE_POSITION = "date_position"
        private const val KEY_TIME_POSITION = "time_position"
        private const val KEY_PEOPLE_COUNT = "count"
    }
}
