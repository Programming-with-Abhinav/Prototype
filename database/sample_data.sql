USE sih26006;

INSERT INTO
    ports (
        name,
        max_draft,
        max_loa,
        max_beam,
        congestion_status,
        latitude,
        longitude
    )
VALUES
    ('Paradip', 14.5, 260, 42, 'Low', 20.264, 86.699),
    (
        'Visakhapatnam',
        15,
        280,
        45,
        'Moderate',
        17.686,
        83.289
    ),
    ('Gangavaram', 16, 290, 45, 'Low', 17.630, 83.235),
    (
        'Gopalpur',
        12.5,
        220,
        35,
        'Moderate',
        19.274,
        84.912
    ),
    ('Dhamra', 14, 250, 40, 'Low', 20.782, 86.947),
    (
        'Sagar-Sandheads',
        11.5,
        210,
        33,
        'Moderate',
        21.692,
        88.039
    ),
    ('Haldia', 11, 200, 32, 'Moderate', 22.025, 88.064);

INSERT INTO
    vessels (vessel_type, capacity_tonnes, draft, loa, beam)
VALUES
    ('Handysize', 35000, 10.5, 180, 28),
    ('Supramax', 58000, 12.8, 200, 32),
    ('Panamax', 75000, 13.2, 225, 32.3),
    ('Capesize', 170000, 18, 289, 45);